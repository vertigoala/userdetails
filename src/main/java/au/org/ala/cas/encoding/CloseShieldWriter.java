package au.org.ala.cas.encoding;

import org.apache.commons.io.output.ProxyWriter;

import java.io.Writer;

public class CloseShieldWriter extends ProxyWriter {

    public CloseShieldWriter(Writer proxy) {
        super(proxy);
    }

    @Override
    public void close() {
    }
}
