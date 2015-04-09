if [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
  echo -e "Updating GH-pages.\n"
  
  cp -R at.fhv.tedapt.releng.p2/target $HOME/p2
  
  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "Travis"
  
  git clone --quiet https://${GH_TOKEN}@github.com/drexedam/drexedam.github.io.git  > /dev/null
  cd drexedam.github.io
  cp -Rf $HOME/p2/ .
  
  git add -f .
  git commit -m "Travis build $TRAVIS_BUILD_NUMBER"
  git push -fq origin > /dev/null
  
  echo -e "Updating done"
  
fi