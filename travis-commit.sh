#!/bin/sh

setup_git() {
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "Travis CI"
}

commit_version_files() {
  git fetch
  git checkout master
  git add -f version.txt next-version.txt
  # with "[skip ci]" to avoid a build loop
  git commit -m "Updating version files (Version $TRAVIS_TAG) (Build $TRAVIS_BUILD_NUMBER)" -m "[skip ci]"
}

upload_files() {
  git push origin master --quiet
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