package bate.papo.uol.util.enums;

import lombok.AllArgsConstructor;

import static io.smallrye.config._private.ConfigLogging.log;

@AllArgsConstructor
public enum MessageTypes {
    MESSAGE,
    PRIVATE_MESSAGE;

    public static MessageTypes from (String value){
        log.info("Rodando o messageTypes");
        if(value == null)
            throw new IllegalArgumentException();
        log.infof(value);
        return  MessageTypes.valueOf(value.trim().toUpperCase());
    }
};
