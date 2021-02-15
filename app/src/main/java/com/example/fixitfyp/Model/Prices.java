package com.example.fixitfyp.Model;

import java.util.HashMap;

public class Prices extends Products{

    public Prices(String tradeName, String tradeEmail, String tradeId,
                  String tradeJob, String tradePhone, HashMap<String, Prices> tradePrice1, HashMap<String, Prices> tradePrice2) {
        super(tradeName, tradeEmail, tradeId, tradeJob, tradePhone, tradePrice1, tradePrice2);
    }


}
