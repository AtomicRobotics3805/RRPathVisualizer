class MechanismController {
    private val servosExtended = false
    val isIntakeOn = false
    val isShooterOn = false
    val isWobbleGoalUp = false
    fun switchWobbleGoal() {}
    fun grabGoal() {}
    fun alignGoal() {}
    fun dropGoal() {}
    fun switchIntake() {}
    fun startIntake() {}
    fun stopIntake() {}
    fun switchShooter() {}
    fun startShooter() {}
    fun stopShooter() {}
    fun shootRing() {}
    fun shootRing(pause: Boolean) {}
    fun retractShooterServos() {}
    fun areServosExtended(): Boolean {
        return servosExtended
    }
}