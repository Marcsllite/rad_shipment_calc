package com.marcsllite.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TableColumn;

/**
 * author Thierry Wasylczenko
 * <a href="https://thierrywasyl.wordpress.com/2012/12/03/tableview-columns-width-using-percentage-in-javafx/">view article</a>
 */
public class PTableColumn<S, T> extends TableColumn<S, T> {
    private final DoubleProperty percentageWidth = new SimpleDoubleProperty(1.0);

    public PTableColumn() {
        tableViewProperty().addListener((ov, t, t1) -> {
            if(PTableColumn.this.prefWidthProperty().isBound()) {
                PTableColumn.this.prefWidthProperty().unbind();
            }

            PTableColumn.this.prefWidthProperty().bind(t1.widthProperty().multiply(percentageWidth));
        });
    }

    public final DoubleProperty percentageWidthProperty() { return this.percentageWidth; }

    public final double getPercentageWidth() { return this.percentageWidthProperty().get(); }

    public final void setPercentageWidth(double value) throws IllegalArgumentException {
        if(value >= 0 && value <= 1) {
            this.percentageWidthProperty().set(value);
        } else {
            throw new IllegalArgumentException(String.format("The provided percentage width is not between 0.0 and 1.0. Value is: %1$s", value));
        }
    }
}
