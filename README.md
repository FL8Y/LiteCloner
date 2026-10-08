# Lite Cloner

**Private-beta rebuild of the EysClone prototype, prepared as a fresh indie-development repository.**

The repository is deliberately split into two parts:

1. **Prototype engine** — the APK you supplied, stored under `vendor/`. This keeps the existing VirtualApp/ADB/control implementation intact while there is no clean Android Studio source tree available.
2. **Fresh additions** — the `app/` module contains the new Shizuku bridge. GitHub Actions builds that bridge and then merges it into a rebuilt copy of the prototype APK.

## Beta behavior

The private-beta build is intended to be:

- called **Lite Cloner** in the app UI;
- **keyless** — activation/key entry is bypassed for this beta build;
- free of the prototype's Discord/community prompts and hard-coded Eys support links;
- free of the prototype's external update/download links;
- able to start the existing `io.virtualapp.standalone.HelperMain` through Shizuku;
- still able to retain the prototype's original helper/control behavior.

This repository does **not** expose, store, or require an activation key.

## Important architecture note

This is a **binary-preserving migration repo**, not a claim that the original application source code has been magically reconstructed. The prototype APK is used as the engine because the public prototype repository did not contain the Android Studio project.

That gives you a practical private-beta path now. Later, the `vendor/` engine can be replaced with a true source migration without changing the public repository shape.

## Build on GitHub

1. Create a new GitHub repository, for example `LiteCloner`.
2. Copy the contents of this ZIP into that new repository.
3. Keep your prototype build at exactly:
   `vendor/EysClone-1.3.59.apk`
4. Commit and push the repository.
5. Open **Actions** in GitHub.
6. Run **Build Lite Cloner Private Beta** with **workflow_dispatch** or push to `main`.
7. Download the `lite-cloner-private-beta` artifact from the workflow run.

The workflow uses GitHub's hosted build environment and installs Gradle 9.5.0, Android SDK 37, and Java 17. The Android Gradle Plugin is pinned to 9.3.0.

## Shizuku setup

Install and start Shizuku on the tester device using Shizuku's normal supported setup for that device. Then open the **Shizuku** launcher entry added by Lite Cloner, grant permission, connect the bridge, and start the helper.

The bridge uses Shizuku's `UserService` architecture. The helper launch mirrors the prototype's existing invocation of `io.virtualapp.standalone.HelperMain` with the final APK as `CLASSPATH`.

## Release signing

The included workflow intentionally signs the private-beta APK with a temporary debug keystore generated during the workflow. That is convenient for closed testing but is **not** a production release key.

When you are ready for long-lived releases, create your own upload/keystore secret and update the release workflow to sign with it. Do not commit a private keystore or passwords to the repository.

## Repository layout

```text
LiteCloner/
├─ .github/
│  └─ workflows/
│     └─ build.yml
├─ app/
│  ├─ build.gradle.kts
│  └─ src/main/
│     ├─ AndroidManifest.xml
│     ├─ aidl/io/litecloner/shizuku/IShizukuBackend.aidl
│     ├─ java/io/litecloner/shizuku/
│     │  ├─ ShizukuManager.java
│     │  ├─ ShizukuSettingsActivity.java
│     │  └─ ShizukuUserService.java
│     └─ res/values/styles.xml
├─ tools/
│  ├─ patch_prototype.py
│  └─ scan_final.py
├─ vendor/
│  ├─ EysClone-1.3.59.apk
│  └─ prototype-notices/
├─ build.gradle.kts
├─ gradle.properties
├─ settings.gradle.kts
├─ LICENSE
└─ THIRD_PARTY_NOTICES.md
```

## Legal / third-party notes

The prototype APK contains third-party components and their license/notice files. Those materials are retained under `vendor/prototype-notices/`. The new Shizuku bridge is separate application code and is MIT-licensed in this repository.

Shizuku API/provider are used from `dev.rikka.shizuku` version `13.1.5`.

Before public distribution, review the notices and licenses of every component you redistribute.

## Private beta checklist

- [ ] Replace the placeholder repository description and screenshots.
- [ ] Decide whether to publish the prototype APK as part of the public repository or move it to a private release asset.
- [ ] Keep signing keys in GitHub Secrets, never in Git.
- [ ] Test Shizuku on both root and ADB-backed Shizuku setups.
- [ ] Test the original ADB/control path after the keyless patch.
- [ ] Test first launch, reboot, helper restart, and Shizuku restart.
- [ ] Verify that no Discord URL, activation prompt, or prototype support link remains in user-facing screens.

## GitHub Pages tester link

The repository includes `docs/index.html` with an **Install / Download APK** button. Enable GitHub Pages from the `docs/` directory and replace the `YOUR_USER/YOUR_REPO` placeholder with the real repository path.
