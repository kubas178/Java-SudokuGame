package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldsObserver implements Observer {

    final Logger logger = LoggerFactory.getLogger(FieldsObserver.class);

    @Override
    public void update1() {
        logger.info("Verify Failed");
    }

}
