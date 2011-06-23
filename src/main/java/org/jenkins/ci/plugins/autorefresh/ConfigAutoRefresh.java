/*
 * The MIT License
 * 
 * Copyright (c) 2011, Jesse Farinacci
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jenkins.ci.plugins.autorefresh;

import hudson.Extension;
import hudson.model.PageDecorator;

import java.util.logging.Logger;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

/**
 * The <a
 * href="http://wiki.jenkins-ci.org/display/JENKINS/Config+AutoRefresh+Plugin"
 * >Config AutoRefresh Plugin</a> provides a way to configure the auto-refresh
 * rate from the Jenkins UI.
 * 
 * @author <a href="mailto:jieryn@gmail.com">Jesse Farinacci</a>
 * @since 0.1
 */
@Extension
public final class ConfigAutoRefresh extends PageDecorator
{
  /**
   * The system property which controls the meta refresh rate of automatic
   * refresh pages.
   * 
   * @see System#getProperty(String)
   */
  public static final String  AUTO_REFRESH_SECONDS = "hudson.Functions.autoRefreshSeconds";

  /**
   * The rate to refresh the page.
   * 
   * @see <a href="http://en.wikipedia.org/wiki/Meta_refresh">Meta refresh</a>
   * @since 0.1
   */
  private int                 refreshRate = Integer.valueOf(10);

  @DataBoundConstructor
  public ConfigAutoRefresh()
  {
    super(ConfigAutoRefresh.class);
    load();
  }

  /**
   * Get the rate to refresh the page.
   * 
   * @return the rate to refresh the page
   */
  public int getRefreshRate()
  {
    return refreshRate;
  }

  /**
   * Set the rate to refresh the page.
   * 
   * @param refreshRate
   *          the rate to refresh the page
   */
  public void setRefreshRate(int refreshRate)
  {
    this.refreshRate = refreshRate;
    System.setProperty(AUTO_REFRESH_SECONDS, Integer.toString(refreshRate));
    save();
  }

  @Override
  public boolean configure(StaplerRequest request, JSONObject jsonObject)
  {
    request.bindJSON(this, jsonObject);
    save();
    return true;
  }

  @Override
  public String getDisplayName()
  {
    return Messages.DisplayName();
  }

  @Override
  public synchronized void load()
  {
    super.load();
    System.setProperty(AUTO_REFRESH_SECONDS, Integer.toString(refreshRate));
  }
}
