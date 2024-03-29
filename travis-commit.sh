#!/bin/sh

setup_git() {
  git config --global user.name "UjuE"
  git config --global user.password $GIT_ACCES_KEY
}

commit_version_files() {
  git remote set-url origin "https://UjuE:${GIT_ACCES_KEY}@github.com/UjuE/whot-engine.git"
  git fetch origin
  git checkout master
  git add -f version.txt next-version.txt
  git commit -m "Updating version files (Version $TRAVIS_TAG) (Build $TRAVIS_BUILD_NUMBER)"
}

upload_files() {
  git push origin HEAD:master
}

setup_git
commit_version_files

# Attempt to commit to git only if "git commit" succeeded
if [ $? -eq 0 ]; then
  echo "Updating version files to github"
  upload_files
else
  echo "No changes in version files."
fi