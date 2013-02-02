#!/usr/bin/env groovy 

/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.xzample.drools

@GrabResolver('https://repository.jboss.org/nexus/content/groups/public-jboss/')
@Grapes([
	      @Grab(group='org.drools', module='drools-core', version='5.4.0.Final'),
	      @Grab(group='org.drools', module='drools-clips', version='5.4.0.Final'),
              @Grab(group='org.drools', module='drools-compiler', version='5.4.0.Final'),
	      @Grab(group='org.drools', module='drools-decisiontables', version='5.4.0.Final'),
	      @Grab(group='org.drools', module='drools-jsr94', version='5.4.0.Final'),
	      @Grab(group='org.drools', module='drools-templates', version='5.4.0.Final'),
	      @Grab(group='com.sun.xml.bind', module='jaxb-xjc', version='2.2.4'),
	      @Grab(group='com.sun.xml.bind', module='jaxb-impl', version='2.2.4'),
	      @Grab(group='javax.xml.stream', module='stax-api', version='1.0-2')
	])

import org.drools.KnowledgeBase
import org.drools.KnowledgeBaseFactory
import org.drools.builder.KnowledgeBuilder
import org.drools.builder.KnowledgeBuilderFactory
import org.drools.builder.ResourceType
import org.drools.definition.KnowledgePackage
import org.drools.event.rule.DebugAgendaEventListener
import org.drools.event.rule.DebugWorkingMemoryEventListener
import org.drools.io.ResourceFactory
import org.drools.logger.KnowledgeRuntimeLogger
import org.drools.logger.KnowledgeRuntimeLoggerFactory
import org.drools.runtime.StatefulKnowledgeSession


/**
 * Simple example that shows how to run drools with groovy.
 * Derived from the drools helloworld example.
 *
 * @author Markus Schneider
 */
void fireAllRules() {
	
	/**
	 * This is a sample file to launch a rule package from a rule source file.
	 */
	KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder()
	
	// this will parse and compile in one step
	kbuilder.add( ResourceFactory.newClassPathResource( "rules.drl",this.class ),ResourceType.DRL )
	
	// Check the builder for errors
	if ( kbuilder.hasErrors() ) {
		 println( kbuilder.getErrors().toString() )
		 throw new RuntimeException( "Unable to compile \"rules.drl\"." )
	}
	
	// get the compiled packages (which are serializable)
        List pkgs = kbuilder.getKnowledgePackages()
	
	// add the packages to a knowledgebase (deploy the knowledge packages).
	KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
	kbase.addKnowledgePackages( pkgs );
	
	StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession()
	//ksession.setGlobal( "list",[])
	
	//ksession.addEventListener( new DebugAgendaEventListener() )
	//ksession.addEventListener( new DebugWorkingMemoryEventListener() )
	
	// setup the audit logging
	// Remove comment to use FileLogger
	// KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger( ksession, "./change-rules" );
			
	// Remove comment to use ThreadedFileLogger so audit view reflects events whilst debugging
	// KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newThreadedFileLogger( ksession, "./change-rules", 1000 );
	
	
	ksession.insert(new Event(uei:"uei.opennms.org/webserver/down",severity:6,priority:2,message:"webserver1 not available"))
	
	ksession.fireAllRules()
	
	// Remove comment if using logging
	// logger.close();
	
	ksession.dispose()
}

fireAllRules()
