package com.prodyna.pac.conference.web.beans;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

public abstract class AbstractEditEntityMaskBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	protected FacesContext facesContext;

	private Long id;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public boolean isNewMode()
	{
		return id == null;
	}

	public abstract String cancelEditing();

	protected String getRootErrorMessage(Exception e)
	{
		// Default to general error message that registration failed.
		String errorMessage = "Saving Confernce failed. See server log for more information";
		if (e == null) {
			// This shouldn't happen, but return the default messages
			return errorMessage;
		}

		// Start with the exception and recurse to find the root cause
		Throwable t = e;
		while (t != null) {
			// Get the message from the Throwable class instance
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}
}
