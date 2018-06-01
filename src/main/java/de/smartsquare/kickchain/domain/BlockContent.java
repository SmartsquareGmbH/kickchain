package de.smartsquare.kickchain.domain;

import de.smartsquare.kickchain.BlockchainException;

public interface BlockContent {

    String getTransactionId();

    String getSignature() throws BlockchainException;

}
