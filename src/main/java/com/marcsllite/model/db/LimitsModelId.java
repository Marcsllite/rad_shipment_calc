package com.marcsllite.model.db;

import java.io.Serializable;
import java.util.Objects;

public class LimitsModelId implements Serializable {
    private static final long serialVersionUID = 7009442724876176627L;
    private String state;
    private String form;

    public LimitsModelId() { }

    public LimitsModelId(String state, String form) {
        this.state = state;
        this.form = form;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        LimitsModelId temp = (LimitsModelId) obj;
        if(!Objects.equals(this.state, temp.state)) {
            return false;
        }
        return Objects.equals(this.form, temp.form);
    }

    @Override
    public int hashCode() {
        int hash = 57;
        hash = 67 * hash + (this.state != null ? this.state.hashCode() : 0);
        hash = 67 * hash + (this.form != null ? this.form.hashCode() : 0);
        return hash;
    }
}
