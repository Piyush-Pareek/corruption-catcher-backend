package com.corruptionfinder.blockchain;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.util.HashSet;
import java.util.Set;

@Node("FinancialEntity")
public class FinancialEntity {

    @Id
    private String name; // The name of the company, fund, or person
    private boolean isSuspicious = false; // By default, everyone is innocent
    // This creates the actual spiderweb line pointing to where the money went
    @Relationship(type = "TRANSFERRED_FUNDS_TO", direction = Relationship.Direction.OUTGOING)
    private Set<FinancialEntity> recipients = new HashSet<>();

    public FinancialEntity(String name) {
        this.name = name;
    }
    public void markAsSuspicious() {
        this.isSuspicious = true;
    }

    public boolean getIsSuspicious() {
        return isSuspicious;
    }
    public void sendMoneyTo(FinancialEntity recipient) {
        recipients.add(recipient);
    }

    public String getName() {
        return name;
    }

    public Set<FinancialEntity> getRecipients() {
        return recipients;
    }
}