# Publishing the private beta

## New repository

Create a brand-new empty GitHub repository. Suggested name:

`LiteCloner`

Then from the extracted ZIP directory:

```bash
git init
git add .
git commit -m "Initial Lite Cloner private beta"
git branch -M main
git remote add origin https://github.com/YOUR_USER/LiteCloner.git
git push -u origin main
```

## Build an APK

Open **Actions** → **Build Lite Cloner Private Beta** → **Run workflow**.

The workflow builds the Shizuku bridge, rebuilds the prototype engine, injects `classes2.dex`, signs the result with a temporary beta keystore, verifies it, scans it for the targeted Discord/license/update strings, and uploads the resulting APK as a workflow artifact.

## Make a tester release

Create a tag:

```bash
git tag v0.1.0
git push origin v0.1.0
```

The same workflow publishes `LiteCloner-private-beta.apk` to the GitHub Release. Testers can use the release page's normal download button.

## Optional GitHub Pages download page

Enable GitHub Pages using the repository's `docs/` folder. Then edit `docs/index.html` and replace:

`YOUR_USER/YOUR_REPO`

with the real repository path.

The button is intentionally a **Download / Install APK** button. On Android, the browser downloads the APK and Android's package installer handles the installation step.

## Signing later

The current private-beta workflow intentionally creates a temporary debug-style signing key. For stable releases, replace that step with your own keystore stored in GitHub Secrets. Never commit the keystore or its passwords.
