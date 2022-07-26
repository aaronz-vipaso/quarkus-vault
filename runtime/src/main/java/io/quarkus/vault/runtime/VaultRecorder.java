package io.quarkus.vault.runtime;

import static java.util.Collections.emptyList;

import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.ConfigSourceProvider;

import io.quarkus.arc.Arc;
import io.quarkus.arc.ArcContainer;
import io.quarkus.runtime.RuntimeValue;
import io.quarkus.runtime.annotations.Recorder;
import io.quarkus.vault.runtime.config.VaultBootstrapConfig;
import io.quarkus.vault.runtime.config.VaultConfigSourceProvider;

@Recorder
public class VaultRecorder {

    private static final EmptyConfigSourceProvider EMPTY = new EmptyConfigSourceProvider();

    private static class EmptyConfigSourceProvider implements ConfigSourceProvider {
        @Override
        public Iterable<ConfigSource> getConfigSources(ClassLoader classLoader) {
            return emptyList();
        }
    }

    public RuntimeValue<ConfigSourceProvider> configure(VaultBootstrapConfig vaultBootstrapConfig) {
        ConfigSourceProvider configSourceProvider = EMPTY;
        if (vaultBootstrapConfig.url.isPresent()) {
            ArcContainer container = Arc.container();
            container.instance(VaultConfigHolder.class).get().setVaultBootstrapConfig(vaultBootstrapConfig);
            configSourceProvider = new VaultConfigSourceProvider(vaultBootstrapConfig);
        }
        return new RuntimeValue<>(configSourceProvider);
    }
}
