package com.prodyna.pac.conference.model;

import java.io.Serializable;

public interface Entity extends Serializable {

	boolean isNew();

	Long getId();
}
