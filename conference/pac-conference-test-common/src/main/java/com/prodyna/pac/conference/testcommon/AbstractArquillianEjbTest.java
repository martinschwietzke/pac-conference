package com.prodyna.pac.conference.testcommon;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;

/**
 * Abstract parent test class for all EJB test.
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@RunWith(Arquillian.class)
public abstract class AbstractArquillianEjbTest {

	@Inject
	protected EntityManager entityManager;

	@Inject
	protected Logger log;
}
