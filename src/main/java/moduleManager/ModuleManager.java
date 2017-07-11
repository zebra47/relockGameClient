package moduleManager;


import exceptionModule.ExceptionModuleInterface;
import ingameModule.IngameModuleInterface;
import loggerModule.LoggerModuleInterface;
import networkModule.NetworkModuleInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import stateData.ClientState;
import stateData.GameState;

import javax.annotation.Resource;
import java.awt.*;

@Component
@PropertySource("classpath:playerSettings.properties")
public class ModuleManager implements ModuleManagerInterface {

    @Resource
    LoggerModuleInterface loggerModule;
    @Resource
    NetworkModuleInterface networkModule;
    @Resource
    IngameModuleInterface ingameModule;
    @Resource
    ExceptionModuleInterface exceptionModule;

    @Autowired
    PlayerSettings playerSettings;

    public void transferClientState(ClientState clientState) {
        clientState.AddClientInfo(playerSettings.getName(), playerSettings.getColor());
        networkModule.sendClientState(clientState);
    }

    public void transferGameState(GameState gameState) {
        if (gameState.getServerException() == null) {
            ingameModule.setGameState(gameState);
        } else {
            exceptionModule.setServerException(gameState.getServerException());
        }
    }

    public void transferLocalException(Exception exception) {
        exceptionModule.setLocalException(exception);
    }

    public void transferLogMessage(String message) {
        loggerModule.logMessage(message);
    }
}
