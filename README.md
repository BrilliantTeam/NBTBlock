# NBTBlock | 方塊資料
遊戲版本：1.14+
> 當帶有 NBT 標籤的方塊被放置後再重新挖起來時，將不會丟失其 NBT 資料

Spigot: https://www.spigotmc.org/resources/nbtblock-light-weight-lag-free-open-source-1-14-1-20-4.116049/<br>
巴哈介紹: https://forum.gamer.com.tw/C.php?bsn=18673&snA=201362&tnum=1

---
[Made for 輝煌伺服器.](https://discord.gg/5MHGpAFGEN "The Copyright of the entire source codes is owned by YT_iceice according to Article 10 the Copyright Law of the Republic of China.")

## 📃 License | 開源許可證

**This project is under CC0-1.0**

**該插件使用 CC0-1.0 開源許可證**

## 🪴 Compile release version | 編譯發行版本

發行版本用於正常使用，不含 TabooLib 本體。

```
./gradlew build
```

## 🌱 Compile development version | 編譯開發版本

開發版包含 TabooLib 本體，用於開發者使用，但不可運作。

```
./gradlew taboolibBuildApi -PDeleteCode
```

> 參數 -PDeleteCode 表示移除所有邏輯代碼以減少體積。
