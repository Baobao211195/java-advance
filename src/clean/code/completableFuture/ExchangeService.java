package clean.code.completableFuture;

import clean.code.completableFuture.Shop.Money;

public class ExchangeService {

    public Double getRate(Money eur, Money usd) {
        return eur.getFixRate() * usd.getFixRate();
    }

}
