package com.marcsllite.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "LIMITS")
@NamedNativeQueries({
    @NamedNativeQuery(name = LimitsModel.GET_IA_LIMITED,
        query = "select IA_Limited from LIMITS where State=:state and Form=:form"),
    @NamedNativeQuery(name = LimitsModel.GET_IA_PACKAGE,
        query = "select IA_Package from LIMITS where State=:state and Form=:form"),
    @NamedNativeQuery(name = LimitsModel.GET_LIMITED,
        query = "select Limited from LIMITS where State=:state and Form=:form")
})
public class LimitsModel extends BaseModel {
    private static final long serialVersionUID = -5280835757871497233L;
    public static final String GET_IA_LIMITED = "LimitsModel.getIALimited";
    public static final String GET_IA_PACKAGE = "LimitsModel.getIAPackage";
    public static final String GET_LIMITED = "LimitsModel.getLimited";

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
    @Column(name = "State")
    private State state;

    @Id
    @Column(name = "Form")
    private Form form;

    @Column(name = "IA_Limited")
    private float ia_limited;

    @Column(name = "IA_Package")
    private float ia_package;

    @Column(name = "Limited")
    private float limited;

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
