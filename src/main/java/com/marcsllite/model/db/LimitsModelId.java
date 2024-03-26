package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Embeddable
public class LimitsModelId implements Serializable {
    private static final long serialVersionUID = 7009442724876176627L;

    public enum State {
        SOLID("Solid"),
        LIQUID("Liquid"),
        GAS("Gas");

        private final String val;

        State(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        public static ObservableList<String> getFxValues() {
            return Arrays.stream(State.values())
                .map(State::getVal)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
    }

    public enum Form {
        NORMAL("Normal"),
        SPECIAL("Special");

        private final String val;

        Form(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        public static ObservableList<String> getFxValues() {
            return Arrays.stream(Form.values())
                .map(Form::getVal)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
    }

    @Column(name = "State", nullable = false)
    @Convert(converter = StateAttrConverter.class)
    private State state;

    @Column(name = "Form", nullable = false)
    @Convert(converter = FormAttrConverter.class)
    private Form form;

    public LimitsModelId() {
        this(State.SOLID, Form.NORMAL);
    }

    public LimitsModelId(State state, Form form) {
        setState(state);
        setForm(form);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
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
        hash = 67 * hash + (this.state != null ? this.state.getVal().hashCode() : 0);
        hash = 67 * hash + (this.form != null ? this.form.getVal().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "(State: \"" + getState() + "\", Form: \"" + getForm() + "\")";
    }
}
