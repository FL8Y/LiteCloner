#!/usr/bin/env bash
set -euo pipefail

repo="${1:-LiteCloner}"
printf 'Fresh repo: %s\n' "$repo"
printf '%s\n' '1. Create the empty GitHub repository.'
printf '%s\n' '2. Copy this directory into it.'
printf '%s\n' '3. git init && git add . && git commit -m "Initial Lite Cloner private beta"'
printf '%s\n' '4. git branch -M main'
printf '%s\n' '5. git remote add origin <YOUR_NEW_REPO_URL>'
printf '%s\n' '6. git push -u origin main'
printf '%s\n' '7. Open GitHub Actions and run Build Lite Cloner Private Beta.'
