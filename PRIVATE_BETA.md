# Private beta handoff

## What changed from the prototype

- Product name: **Lite Cloner**
- Activation/key gate: **disabled for this beta pipeline**
- Discord/community UI: **removed by the patch step**
- Prototype update/download/community links: **removed by the patch step**
- New Shizuku bridge: **included and merged into the rebuilt APK**
- Existing prototype engine: **retained as the vendor APK and rebuilt through Apktool**

## What testers should see

The normal app should open without asking for an activation key. A separate **Shizuku** launcher entry is added so testers can grant Shizuku access and start the existing helper process through Shizuku.

## What is intentionally not in this beta

There is no activation server configuration, Discord invite, webhook setup, or public support URL in the new beta configuration.
