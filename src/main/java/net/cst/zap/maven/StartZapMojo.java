package net.cst.zap.maven;

import net.cst.zap.commons.boot.Zap;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Starts ZAP.
 * <p>
 * Normally this goal will be used along with the {@code seleniumAnalyze} goal.
 * <p>
 * The usual configuration is to use {@code startZap} in the <i>pre-integration-test</i>,
 * to make sure ZAP is running during the tests. If the tests are correctly configured,
 * they will use ZAP's proxy to run the tests. The goal {@code seleniumAnalyze} can then
 * be configured to run in the phase <i>post-integration-test</i> to run a ZAP analysis
 * without a Spider (using the navigation done by the tests).
 *
 * @author pdsec
 */
@Mojo(name = "startZap")
public class StartZapMojo extends ZapMojo {

  @Override
  public void doExecute() throws MojoExecutionException, MojoFailureException {
    Zap.startZap(buildZapInfo());
  }

}
