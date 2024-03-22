const core = require("@actions/core")

try {
    const message = core.getInput("MESSAGE")
    console.log(`echo input message: ${message}`)
} catch (error) {
    core.setFailed(error.message)
}
