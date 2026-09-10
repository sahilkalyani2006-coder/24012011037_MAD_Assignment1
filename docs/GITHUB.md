# GitHub setup

Use the repository name **24012011037_MAD_Assignment1**.
No GitHub repository has been created in this session because the GitHub plugin
has not been connected.

Once connected, upload this project to that repository. If doing it from Android
Studio, connect your GitHub account under Version Control settings, enable Git
for this project, commit the files, then use Share Project on GitHub. Use an
existing repository if the connected assistant has already created it.

Include source, XML resources, documentation, the Gradle setup and the .github
workflow. After successful wrapper setup, also commit gradle-wrapper.jar.
The .gitignore excludes local.properties, build output, signing keys and caches.

Example initial commit message: `Add offline campus map and route finder`.
The README should describe the built-in map and keep the entrance accuracy
limitation until those entrances have been checked on campus.

Future updates can correct verified entrance points, add confirmed walking paths,
fix issues found during testing and add actual device screenshots. Update the README
with each change and its real verification result. Make dated commits only when
there is real progress.

The Actions workflow builds and checks the project after pushes to main and on
pull requests. It is prepared, but has not run yet. It does not create daily commits.
