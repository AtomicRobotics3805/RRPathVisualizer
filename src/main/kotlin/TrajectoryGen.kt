import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import org.firstinspires.ftc.teamcode.hardware.compbot.DriveConstantsComp

object TrajectoryGen {
    enum class Color {
        BLUE,
        RED
    }

    val color = Color.BLUE

    private val startPose = Pose2d(-63.0, 48.0.y, 0.0.toRadians)

    private val powerShotPose = listOf(Vector2d(72.0, 20.0), Vector2d(72.0, 12.0), Vector2d(72.0, 1.0))

    private val towerPose = Vector2d(72.0, 26.0)

    // number of degrees the shooter shoots offcenter
    private const val OFFSET = 14.3

    private val drive = TrajectoryBuild(DriveConstantsComp)

    private val mech = MechanismController()

    // travel to drop zone, drop wobble goal between movements, prepare to shoot rings
    private val startToLow = drive.trajectoryBuilder(startPose, startPose.heading)
        .splineTo(Vector2d(3.0, 52.0.y), 320.0.a.flip.toRadians)
        .addDisplacementMarker{ mech.dropGoal() }
        .build()
    private val startToMid = drive.trajectoryBuilder(startPose, startPose.heading)
        .splineTo(Vector2d(14.0, 40.0), 270.0.toRadians)
        .addDisplacementMarker{ mech.dropGoal() }
        .build()
    private val startToHigh = drive.trajectoryBuilder(startPose, startPose.heading)
        .splineToConstantHeading(Vector2d(58.0, 44.0), 0.0.toRadians)
        .addDisplacementMarker{ mech.dropGoal() }
        .build()

    private val lowToPowershot = drive.trajectoryBuilder(startToLow.end(), startToLow.end().heading - 90.0.toRadians)
        .splineToLinearHeading(Pose2d(-7.0, 26.0, powerShotAngle(Vector2d(-7.0, 26.0), 0)), 270.0.toRadians)
        .build()
    private val midToPowershot = drive.trajectoryBuilder(startToMid.end(), startToMid.end().heading - 90.0.toRadians)
        .splineToLinearHeading(Pose2d(-7.0, 26.0, powerShotAngle(Vector2d(-7.0, 26.0), 0)), 270.0.toRadians)
        .build()
    private val highToPowershot = drive.trajectoryBuilder(startToHigh.end(), startToHigh.end().heading - 135.0.toRadians)
        .splineToLinearHeading(Pose2d(-7.0, 26.0, powerShotAngle(Vector2d(-7.0, 26.0), 0)), 90.0.toRadians)
        .build()

    private val powershotToWobble = drive.trajectoryBuilder(Pose2d(lowToPowershot.end().vec(), powerShotAngle(lowToPowershot.end().vec(), 2)), powerShotAngle(midToPowershot.end().vec(), 2))
        .splineTo(Vector2d(-48.0, 40.0), 180.0.toRadians)
        .build()
    private val powershotToRingToWobble = drive.trajectoryBuilder(Pose2d(midToPowershot.end().vec(), powerShotAngle(midToPowershot.end().vec(), 2)), powerShotAngle(midToPowershot.end().vec(), 2))
        .splineTo(Vector2d(-20.0, 34.0), 135.0.toRadians)
        .splineTo(Vector2d(-48.0, 40.0), 180.0.toRadians)
        .build()

    private val powershotToRings = drive.trajectoryBuilder(Pose2d(midToPowershot.end().vec(), powerShotAngle(midToPowershot.end().vec(), 2)), powerShotAngle(midToPowershot.end().vec(), 2) - 90.0.toRadians)
        .splineToLinearHeading(Pose2d(-20.0, 36.0, towerAngle(Vector2d(-20.0, 34.0))), 180.0.toRadians)
        .build()
    private val ringsToRingsFurther = drive.trajectoryBuilder(powershotToRings.end(), powershotToRings.end().heading)
        .splineToConstantHeading(Vector2d(-24.0, 36.0), 180.0.toRadians)
        .build()

    private val ringsToWobble = drive.trajectoryBuilder(ringsToRingsFurther.end(), ringsToRingsFurther.end().heading)
        .splineTo(Vector2d(-48.0, 40.0), 180.0.toRadians)
        .build()

    private val wobbleToShootTower =
        drive.trajectoryBuilder(powershotToWobble.end(), true)
            .splineTo(Vector2d(-10.0, 32.0), towerAngle(Vector2d(-10.0, 32.0)) + 180.0.toRadians)
            .build()

    private val wobbleToLow =
        drive.trajectoryBuilder(powershotToWobble.end(), true)
            .splineToLinearHeading(Pose2d(6.0, 46.0.y, 0.0.toRadians), 320.0.a.flip.toRadians)
            .addDisplacementMarker{ mech.dropGoal() }
            .build()
    private val wobbleToHigh =
        drive.trajectoryBuilder(powershotToWobble.end(), powershotToWobble.end().heading + 90.0.toRadians)
            .splineToLinearHeading(Pose2d(60.0, 44.0.y, 0.0.toRadians), 10.0.toRadians)
            .addDisplacementMarker{ mech.dropGoal() }
            .build()

    private val lowToPark =
        drive.trajectoryBuilder(wobbleToLow.end(), wobbleToLow.end().heading - 90.0.toRadians)
            .splineTo(Vector2d(10.0, 28.0.y), 270.0.a.toRadians)
            .build()
    private val highToPark =
        drive.trajectoryBuilder(wobbleToHigh.end(), wobbleToHigh.end().heading - 135.0.toRadians)
            .splineTo(Vector2d(10.0, 28.0.y), 180.0.a.toRadians)
            .build()

    private val towerToMidToPark =
        drive.trajectoryBuilder(wobbleToShootTower.end(), true)
            .splineTo(Vector2d(14.0, 36.0), 90.0.toRadians)
            .addDisplacementMarker{ mech.dropGoal() }
            .build()

    fun createTrajectory(): ArrayList<Trajectory> {
        return createTrajectoryC()
    }

    fun createTrajectories() {

    }

    private fun createTrajectoryA(): ArrayList<Trajectory> {
        return arrayListOf(startToLow, lowToPowershot, powershotToWobble, wobbleToLow, lowToPark)
    }

    private fun createTrajectoryB(): ArrayList<Trajectory> {
        return arrayListOf(startToMid, midToPowershot, powershotToRingToWobble, wobbleToShootTower, towerToMidToPark)
    }

    private fun createTrajectoryC(): ArrayList<Trajectory> {
        return arrayListOf(startToHigh, highToPowershot, powershotToRings, ringsToRingsFurther, ringsToWobble, wobbleToHigh, highToPark)
    }

    fun drawOffbounds() {
        GraphicsUtil.fillRect(Vector2d(0.0, -63.0), 18.0, 18.0) // robot against the wall
    }

    private fun towerAngle(position: Vector2d): Double {
        return (position - towerPose angleBetween Vector2d(1.0, 0.0)) + OFFSET.toRadians
    }

    private fun powerShotAngle(position: Vector2d, num: Int): Double {
        return (position - powerShotPose[num] angleBetween Vector2d(1.0, 0.0)) + OFFSET.toRadians
    }
}

val Double.toRadians get() = (Math.toRadians(this))

val Double.a get () = (if (TrajectoryGen.color == TrajectoryGen.Color.BLUE) this else 360 - this)

val Double.flip get () = (if (TrajectoryGen.color == TrajectoryGen.Color.BLUE) this else {
    if(this - 180 < 0) this + 180
    this - 180
})

val Double.y get () = (if (TrajectoryGen.color == TrajectoryGen.Color.BLUE) this else this * -1)