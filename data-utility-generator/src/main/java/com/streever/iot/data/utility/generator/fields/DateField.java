package com.streever.iot.data.utility.generator.fields;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.streever.iot.data.utility.generator.fields.support.Range;
import com.streever.iot.data.utility.generator.fields.support.StartStopState;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties({"df", "diff", "startStop", "lastIssued"})
public class DateField extends FieldBase<String> {
    private Range<Date> range;
    private Long diff;
    private Long startStopSpan = 100000l;
    private String format = "yyyy-MM-dd HH:mm:ss";
    private DateFormat df = new SimpleDateFormat(format);
    private Boolean current = Boolean.TRUE;
    //private Boolean startStop = Boolean.FALSE;
    private Long lastIssued;

    public Range<Date> getRange() {
        return range;
    }

    public void setRange(Range<Date> range) {
        this.range = range;
        this.diff = range.getMax().getTime() - range.getMin().getTime();
        // When Range is set, don't need current.
        this.current = Boolean.FALSE;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
        df = new SimpleDateFormat(this.format);
    }

    @Override
    public String getNext() {
        if (current) {
            return df.format(new Date());
        } else {
            Long dateValue = null;
            if (getStartStopState().equals(StartStopState.START)) {
                double multiplierD = randomizer.nextDouble();
                dateValue = range.getMin().getTime() + Math.round((Long) diff * multiplierD);
                lastIssued = dateValue;
            } else if (getStartStopState().equals(StartStopState.STOP)) {
                double multiplierD = randomizer.nextDouble();
                dateValue = lastIssued + Math.round((Long) startStopSpan * multiplierD);
            } else {
                double multiplierD = randomizer.nextDouble();
                dateValue = range.getMin().getTime() + Math.round((Long) diff * multiplierD);
                lastIssued = null;
            }
            return df.format(new Date(dateValue));
        }
    }
}
