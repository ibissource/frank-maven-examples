package org.ibissource.custom;

import nl.nn.adapterframework.core.IPipe;
import nl.nn.adapterframework.core.PipeRunResult;
import nl.nn.adapterframework.core.IPipeLineSession;
import nl.nn.adapterframework.configuration.ConfigurationException;
import nl.nn.adapterframework.stream.Message;
import nl.nn.adapterframework.core.PipeRunException;
import nl.nn.adapterframework.pipes.FixedForwardPipe;
import nl.nn.adapterframework.doc.IbisDoc;

import java.io.IOException;

public class CustomPipe extends FixedForwardPipe {
    private static final int ADDITION = 3;

    private String customAttribute = "0";

    @IbisDoc({"1", "This is a custom property"})
    public void setCustomAttribute(String value) {
        customAttribute = value;
    }

    /*
    public String getCustomAttribute() {
        return customAttribute;
    }
    */

    @Override
    public PipeRunResult doPipe(Message message, IPipeLineSession session) throws PipeRunException {
        try {
            String msg = message.asString();
            int val = Integer.parseInt(msg);
            int attributeVal = Integer.parseInt(customAttribute);
            int result = val + attributeVal + ADDITION;
            return new PipeRunResult(getForward(), (Object) Integer.valueOf(result));
        } catch(IOException e) {
            throw new PipeRunException(this, "An IOException occurred", e);
        }
    }

    @Override
    public int getMaxThreads() {
        return 0;
    }
}