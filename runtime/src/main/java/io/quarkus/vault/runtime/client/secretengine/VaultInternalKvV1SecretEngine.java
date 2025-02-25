package io.quarkus.vault.runtime.client.secretengine;

import java.util.Map;

import jakarta.inject.Singleton;

import io.quarkus.vault.runtime.client.VaultClient;
import io.quarkus.vault.runtime.client.VaultInternalBase;
import io.quarkus.vault.runtime.client.dto.kv.VaultKvListSecrets;
import io.quarkus.vault.runtime.client.dto.kv.VaultKvSecretJsonV1;
import io.smallrye.mutiny.Uni;

@Singleton
public class VaultInternalKvV1SecretEngine extends VaultInternalBase {

    @Override
    protected String opNamePrefix() {
        return super.opNamePrefix() + " [KV (v1)]";
    }

    public Uni<Void> deleteSecret(VaultClient vaultClient, String token, String secretEnginePath, String path) {
        return vaultClient.delete(opName("Delete Secret"), secretEnginePath + "/" + path, token, 204);
    }

    public Uni<Void> writeSecret(VaultClient vaultClient, String token, String secretEnginePath, String path,
            Map<String, String> secret) {
        return vaultClient.post(opName("Write Secret"), secretEnginePath + "/" + path, token, secret, null, 204);
    }

    public Uni<VaultKvSecretJsonV1> getSecretJson(VaultClient vaultClient, String token, String secretEnginePath, String path) {
        return vaultClient.get(opName("Get Secret"), secretEnginePath + "/" + path, token, VaultKvSecretJsonV1.class);
    }

    public Uni<VaultKvListSecrets> listSecrets(VaultClient vaultClient, String token, String secretEnginePath, String path) {
        return vaultClient.list(opName("List Secrets"), secretEnginePath + "/" + path, token, VaultKvListSecrets.class);
    }
}
