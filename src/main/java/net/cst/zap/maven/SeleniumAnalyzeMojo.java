package net.cst.zap.maven;

import net.cst.zap.api.ZapClient;
import net.cst.zap.api.model.AnalysisInfo;
import net.cst.zap.api.model.AnalysisType;
import net.cst.zap.api.model.AuthenticationInfo;
import net.cst.zap.api.report.ZapReport;
import net.cst.zap.commons.ZapInfo;
import net.cst.zap.commons.boot.Zap;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Run ZAP's Active Scan and generates the reports. <b>No Spider is executed.</b>
 * This scan assumes that integration tests ran using ZAP as a proxy, so the Active Scan
 * is able to use the navigation done during the tests for the scan.
 * <p>
 * Normally this goal will be executed in the phase <i>post-integration-test</i>, while the
 * goal {@code startZap} will run in the phase <i>pre-integration-test</i>, to make sure
 * ZAP is running during the tests.
 *
 * @author pdsec
 */
@Mojo(name = "seleniumAnalyze")
public class SeleniumAnalyzeMojo extends ZapMojo {

  @Override
  public void doExecute() throws MojoExecutionException, MojoFailureException {
    getLog().info("Starting ZAP analysis at target: " + super.getTargetUrl());

    ZapInfo zapInfo = buildZapInfo();
    AuthenticationInfo authenticationInfo = buildAuthenticationInfo();
    AnalysisInfo analysisInfo = buildAnalysisInfo(AnalysisType.ACTIVE_SCAN_ONLY);

    ZapClient zapClient = new ZapClient(zapInfo, authenticationInfo);
    try {
      ZapReport zapReport = zapClient.analyze(analysisInfo);
      saveReport(zapReport);
      analyzeReport(zapReport, zapInfo);
    }
    finally {
      Zap.stopZap();
    }

    getLog().info("ZAP analysis finished.");
  }

}
