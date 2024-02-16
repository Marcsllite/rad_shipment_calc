package com.marcsllite.model.db;

import java.io.Serializable;
import java.util.Objects;

public class LimitsModelId implements Serializable {
    private static final long serialVersionUID = 7009442724876176627L;
    private LimitsModel.State state;
    private LimitsModel.Form form;

    public LimitsModelId() {
        this(LimitsModel.State.SOLID, LimitsModel.Form.NORMAL);
    }

    public LimitsModelId(LimitsModel.State state, LimitsModel.Form form) {
        setState(state);
        setForm(form);
    }

    public LimitsModel.State getState() {
        return state;
    }

    public void setState(LimitsModel.State state) {
        this.state = state;
    }

    public LimitsModel.Form getForm() {
        return form;
    }

    public void setForm(LimitsModel.Form form) {
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
