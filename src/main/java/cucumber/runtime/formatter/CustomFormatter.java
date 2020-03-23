package cucumber.runtime.formatter;

import app.common.Context;
import cucumber.api.Result;
import cucumber.api.TestCase;
import cucumber.api.TestStep;
import cucumber.api.event.*;
import cucumber.api.formatter.Formatter;
import gherkin.ast.Feature;
import gherkin.ast.ScenarioDefinition;
import gherkin.ast.Step;
import gherkin.ast.Tag;
import gherkin.pickles.Argument;
import gherkin.pickles.PickleString;
import gherkin.pickles.PickleTable;
import gherkin.pickles.PickleTag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomFormatter implements Formatter {

    private final TestSourcesModel testSources = new TestSourcesModel();

    private ReportDriver reports;
    private TagFilterService tagFilterService;
    private Context context;
    private String currentFeatureFile;

    private Boolean hasBackGround = false;

    private List<TestStep> backGroundList = new ArrayList<>();
    private List<Result> backGroundResultList = new ArrayList<>();

    public CustomFormatter(String sReportType) {
        context = Context.getInstance();
        reports = context.getReports(sReportType);
        tagFilterService = new TagFilterService();
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestSourceRead.class, testSourceReadHandler);
        publisher.registerHandlerFor(TestRunStarted.class, runStartedHandler);
        publisher.registerHandlerFor(TestRunFinished.class, runFinishedHandler);
        publisher.registerHandlerFor(TestCaseStarted.class, caseStartedHandler);
        publisher.registerHandlerFor(TestCaseFinished.class, caseFinishedHandler);
        publisher.registerHandlerFor(TestStepStarted.class, stepStartedHandler);
        publisher.registerHandlerFor(TestStepFinished.class, stepFinishedHandler);
    }

    private EventHandler<TestSourceRead> testSourceReadHandler = this::handleTestSourceRead;
    private EventHandler<TestRunStarted> runStartedHandler = this::handleTestRunStarted;
    private EventHandler<TestRunFinished> runFinishedHandler = this::handleTestRunFinished;
    private EventHandler<TestCaseStarted> caseStartedHandler = this::handleTestCaseStarted;
    private EventHandler<TestCaseFinished> caseFinishedHandler = this::handleTestCaseFinished;
    private EventHandler<TestStepStarted> stepStartedHandler = this::handleTestStepStarted;
    private EventHandler<TestStepFinished> stepFinishedHandler = this::handleTestStepFinished;

    private void handleTestSourceRead(TestSourceRead event) {
        testSources.addTestSourceReadEvent(event.uri, event);
    }

    private void handleTestRunStarted(TestRunStarted event) {
        //
    }

    private void handleTestRunFinished(TestRunFinished event) {
        reports.endExecution();
    }

    private void handleTestCaseStarted(TestCaseStarted event) {
        reports.getReportMeta().initializeStep();           //This will set the step number as zero as we are started the Test Case
        handleStartOfFeature(event.testCase);
        handleBackGround(event.testCase);
        handleScenario(event.testCase);
    }

    private void handleTestCaseFinished(TestCaseFinished event) {
        reports.endTest(event.result.getDuration(), event.result.getStatus(), event.result.getErrorMessage());
    }

    private void handleTestStepStarted(TestStepStarted event) {
        if(!event.testStep.isHook()) {
            if(!isBackGroundStep(event.testStep)) {
                backGroundList.forEach( bcList -> handleStep(bcList));
                backGroundResultList.forEach( bcListResult -> reports.endStep(bcListResult.getDuration(), bcListResult.getStatus(), bcListResult.getErrorMessage()));

                hasBackGround = false;
                backGroundList.clear();
                backGroundResultList.clear();
            }

            if(hasBackGround) {
                backGroundList.add(event.testStep);
            } else {
                handleStep(event.testStep);
            }
        }
    }

    private boolean isBackGroundStep(TestStep testStep) {
        TestSourcesModel.AstNode astNode = testSources.getAstNode(currentFeatureFile, testStep.getStepLine());
        if(astNode != null) {
            return TestSourcesModel.isBackgroundStep(astNode);
        }
        return false;
    }

    private void handleStep(TestStep testStep) {
        TestSourcesModel.AstNode astNode = testSources.getAstNode(currentFeatureFile, testStep.getStepLine());
        if(astNode != null) {
            Step step = (Step) astNode.node;
            if(testStep.getStepArgument().isEmpty()) {          //Need to understand what is step Argument
                reports.startStep(reports.getReportMeta().getCurrentStepNumber(), step.getKeyword(), step.getText());
            } else {
                Argument argument = testStep.getStepArgument().get(0);
                if(argument instanceof PickleString) {
                    reports.startStep(reports.getReportMeta().getCurrentStepNumber(), step.getKeyword(), step.getText(), ((PickleString) argument).getContent());
                } else if (argument instanceof PickleTable) {
                    reports.startStep(reports.getReportMeta().getCurrentStepNumber(), step.getKeyword(), step.getText(), (PickleTable) argument);
                }
            }
        }
    }

    private void handleTestStepFinished(TestStepFinished event) {
        if(!event.testStep.isHook()) {
            if(hasBackGround) {
                backGroundResultList.add(event.result);
            } else {
                reports.endStep(event.result.getDuration(),
                        //if the testStep is passed but if any earlier steps are failed then we need to set the test step to failed status due to earlier failures
                        (event.result.getStatus().equals(Result.Type.PASSED) && reports.getReportMeta().isbStepFailure()) ? Result.Type.FAILED : event.result.getStatus(),
                        event.result.getErrorMessage());
            }
        }
        reports.getReportMeta().incrementStepNumber();
    }

    public void handleStartOfFeature(TestCase testCase) {
        if(currentFeatureFile == null || !currentFeatureFile.equals(testCase.getUri())) {
            if(currentFeatureFile != null) {
                reports.getReportMeta().setNewFeatureFile(true);        //if the value is not null and does not equal to currentFeatureFile value this flag is set to True
            }
            currentFeatureFile = testCase.getUri();
            reports.getReportMeta().setCurrentFeatureFile(currentFeatureFile);
            addFeature(testSources.getFeature(testCase.getUri()));
        }
    }

    private void addFeature(Feature feature) {
        List<String> featureTags = feature.getTags().stream().map(Tag::getName).collect(Collectors.toList());
        reports.getReportMeta().setFeatureTags(featureTags);
        reports.addFeature(feature.getKeyword(), feature.getName(), feature.getDescription(), featureTags);
    }

    private void handleBackGround(TestCase testCase) {
        if(testSources.hasBackground(testCase.getUri(), testCase.getLine())) {
            hasBackGround = true;
        }
    }

    private void handleScenario(TestCase testCase) {
        TestSourcesModel.AstNode astNode = testSources.getAstNode(testCase.getUri(), testCase.getLine());
        if (astNode != null) {
            ScenarioDefinition scenarioDefinition = TestSourcesModel.getScenarioDefinition(astNode);
            List<String> scenarioTags = testCase.getTags().stream().map(PickleTag::getName).collect(Collectors.toList());
            scenarioTags = tagFilterService.getFilteredTags(scenarioTags, reports.getReportMeta().getFeatureTgs());
            reports.startTest(scenarioDefinition.getKeyword(), scenarioDefinition.getName(), scenarioDefinition.getDescription(), scenarioTags);
        }
    }

}
