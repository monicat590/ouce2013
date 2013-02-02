package org.xzample.drools


class Event {

	String uei 
        int severity 
        int priority
        String message

	String getUei() {
		this.uei
	}

	void setUei() {
		this.uei = uei 
	}

	int getSeverity() {
		this.severity
	}

	void setSeverity(severity) {
		this.severity = severity 
	}

	String getPriority() {
		this.priority
	}

	void setPriority(priority) {
		this.priority = priority 
	}

	String getMessage() {
		this.message
	}

	void setMessage(message) {
		this.message = message 
	}
}
