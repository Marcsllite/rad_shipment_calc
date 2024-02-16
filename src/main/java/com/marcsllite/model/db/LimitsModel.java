package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "LIMITS")
public class LimitsModel extends BaseModel {
    private static final long serialVersionUID = -5280835757871497233L;

    public enum State {
        SOLID("Solid"),
        LIQUID("Liquid"),
        GAS("Gas");

        public final String val;

        State(String val) {
            this.val = val;
        }
    }

    public enum Form {
        NORMAL("Normal"),
        SPECIAL("Special");

        public final String val;

        Form(String val) {
            this.val = val;
        }
    }
    @Id
    @Column(name = "State", nullable = false)
    private State state;

    @Id
    @Column(name = "Form", nullable = false)
    private Form form;

    @Column(name = "IA_Limited")
    private float ia_limited;

    @Column(name = "IA_Package")
    private float ia_package;

    @Column(name = "Limited")
    private float limited;

    public LimitsModel(State state, Form form, float ia_limited, float ia_package, float limited) {
        this.state = state;
        this.form = form;
        this.ia_limited = ia_limited;
        this.ia_package = ia_package;
        this.limited = limited;
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

    public float getIa_limited() {
        return ia_limited;
    }

    public void setIa_limited(float ia_limited) {
        this.ia_limited = ia_limited;
    }

    public float getIa_package() {
        return ia_package;
    }

    public void setIa_package(float ia_package) {
        this.ia_package = ia_package;
    }

    public float getLimited() {
        return limited;
    }

    public void setLimited(float limited) {
        this.limited = limited;
    }
}
