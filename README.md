# RWGFY

![screenshot](scr.jpg)

[<img alt="alt_text" width="250px" src="https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png" />](https://play.google.com/store/apps/details?id=dev.rostopira.rwgfy&utm_source=github&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1)

This project is a tile for Wear OS that shows everyday losses of RF AF (aka occupiers).

Data is fetched from API: https://russianwarship.rip/ 

It attempts to refresh itself everyday in 8am (GMT+2) with 15 minutes retry.

Future releases will show more stats (like loses of tanks and artillery).

## Building project

Before building project run
```shell
./gradlew openApiGenerate
```

Then run as usual using Android Studio.
