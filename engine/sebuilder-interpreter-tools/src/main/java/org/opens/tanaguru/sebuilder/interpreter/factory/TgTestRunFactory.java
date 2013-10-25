/*
 *  Tanaguru - Automated webpage assessment
 *  Copyright (C) 2008-2013  Open-S Company
 * 
 *  This file is part of Tanaguru.
 * 
 *  Tanaguru is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 * 
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 *  Contact us by mail: open-s AT open-s DOT com
 */

package org.opens.tanaguru.sebuilder.interpreter.factory;

import com.sebuilder.interpreter.Script;
import com.sebuilder.interpreter.TestRun;
import com.sebuilder.interpreter.factory.TestRunFactory;
import com.sebuilder.interpreter.webdriverfactory.WebDriverFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.opens.tanaguru.sebuilder.interpreter.NewPageListener;
import org.opens.tanaguru.sebuilder.interpreter.TgTestRun;
import org.opens.tanaguru.sebuilder.interpreter.webdriverfactory.FirefoxDriverFactory;

/**
 *
 * @author jkowalczyk
 */
public class TgTestRunFactory extends TestRunFactory {

    /**
     * The firefox profile used when the browser is loaded
     */
    WebDriverFactory webDriverFactory = new FirefoxDriverFactory();
//    WebDriverFactory webDriverFactory = new PhantomJsFactory();

    /**
     * The firefox profile used when the browser is loaded
     */
    FirefoxProfile firefoxProfile = new FirefoxProfile();
    public void setFirefoxProfile(FirefoxProfile firefoxProfile) {
        this.firefoxProfile = firefoxProfile;
    }
    
    /**
     * the map that handles js scripts executed when page is loaded
     */
    private Map<String, String> jsScriptMap;
    public Map<String, String> getJsScriptMap() {
        return jsScriptMap;
    }
    public void setJsScriptMap(Map<String, String> jsScriptMap) {
        this.jsScriptMap = jsScriptMap;
    }
    
    /**
     * The new page listeners
     */
    private Collection<NewPageListener> newPageListeners;
    public void addNewPageListener(NewPageListener newPageListener) {
        if (newPageListeners == null) {
            newPageListeners = new ArrayList<NewPageListener>();
        }
        this.newPageListeners.add(newPageListener);
    }

    public void addNewPageListeners(Collection<NewPageListener> newPageListeners) {
        if (this.newPageListeners == null) {
            this.newPageListeners = new ArrayList<NewPageListener>();
        }
        this.newPageListeners.addAll(newPageListeners);
    }

    public void removeNewPageListener(NewPageListener newPageListener) {
        if (newPageListeners != null) {
            this.newPageListeners.remove(newPageListener);
        }
    }

    public void removeNewPageListeners(Collection<NewPageListener> newPageListeners) {
        if (newPageListeners != null) {
            this.newPageListeners.removeAll(newPageListeners);
        }
    }

//    private FirefoxDriverObjectPool fdop;
//    public void setFirefoxDriverObjectPool(FirefoxDriverObjectPool fdop) {
//        this.fdop = fdop;
//    }
    
    @Override
    public TestRun createTestRun(Script script) {
        if (webDriverFactory instanceof FirefoxDriverFactory) {
            ((FirefoxDriverFactory)webDriverFactory).setFirefoxProfile(firefoxProfile);
        }
        TgTestRun testRun = new TgTestRun(
                script, 
                webDriverFactory, 
                new HashMap<String, String>(), 
                getImplicitelyWaitDriverTimeout(), 
                getPageLoadDriverTimeout());
        testRun.addNewPageListeners(newPageListeners);
        testRun.setJsScriptMap(jsScriptMap);
        return testRun;
    }
    
    @Override
    public TestRun createTestRun(Script script, Log log, WebDriverFactory webDriverFactory, HashMap<String, String> webDriverConfig) {
        if (webDriverFactory instanceof FirefoxDriverFactory) {
            ((FirefoxDriverFactory)webDriverFactory).setFirefoxProfile(firefoxProfile);
        }
        TgTestRun testRun = 
                new TgTestRun(
                    script, 
                    log, 
                    webDriverFactory, 
                    webDriverConfig, 
                    getImplicitelyWaitDriverTimeout(), 
                    getPageLoadDriverTimeout());
        testRun.addNewPageListeners(newPageListeners);
        testRun.setJsScriptMap(jsScriptMap);
        return testRun;
    }

}