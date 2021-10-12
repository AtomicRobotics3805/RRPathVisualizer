import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.Trajectory

object TrajectoryGen {
    enum class Color {
        BLUE,
        RED
    }

    val color = Color.BLUE

    private val carouselStartPose = Pose2d(-36.0, 63.0.switchColor, (if (color == Color.BLUE) 180.0 else 90.0).switchColorAngle.toRadians)
    private val farParkStartPose = Pose2d(-36.0, 63.0.switchColor, 0.0.switchColorAngle.toRadians)
    private val closeParkStartPose = Pose2d(6.0, 63.0.switchColor, 0.0.switchColorAngle.toRadians)
    private val hubStartPose = Pose2d(-12.0, 63.0.switchColor, 270.0.switchColorAngle.toRadians)

    private val drive = TrajectoryBuild(DriveConstantsComp)

    private val startToHub = drive.trajectoryBuilder(hubStartPose, hubStartPose.heading)
        .forward(21.0)
        .build()

    private val startToCarouselBlue = drive.trajectoryBuilder(carouselStartPose, carouselStartPose.heading)
        .splineToConstantHeading(Vector2d(-53.5, 62.0.switchColor), 175.0.switchColorAngle.toRadians)
        .build()
    private val startToCarouselRed = drive.trajectoryBuilder(carouselStartPose, carouselStartPose.heading + 130.0.switchColorAngle.toRadians)
        .splineToSplineHeading(Pose2d(-56.5, 59.0.switchColor, 120.0.switchColorAngle.toRadians), 200.0.switchColorAngle.toRadians)
        .build()
    private val startToParkFar = drive.trajectoryBuilder(farParkStartPose, farParkStartPose.heading - 4.0.switchColorAngle.toRadians)
        .splineToConstantHeading(Vector2d(40.0, 61.0.switchColor), 0.0.switchColorAngle.toRadians)
        .build()
    private val startToParkClose = drive.trajectoryBuilder(closeParkStartPose, closeParkStartPose.heading - 10.0.switchColorAngle.toRadians)
        .splineToConstantHeading(Vector2d(40.0, 61.0.switchColor), 0.0.switchColorAngle.toRadians)
        .build()

    private val carouselToHub = drive.trajectoryBuilder((if (color == Color.BLUE) startToCarouselBlue else startToCarouselRed).end(), true)
        .splineToSplineHeading(Pose2d(-12.0, 42.0.switchColor, 270.0.switchColorAngle.toRadians), 320.0.switchColorAngle.toRadians)
        .build()

    private val hubToPark = drive.trajectoryBuilder(carouselToHub.end(), carouselToHub.end().heading + 90.0.switchColorAngle.toRadians)
        .splineToSplineHeading(Pose2d(20.0, 42.0.switchColor, 180.0.switchColorAngle.toRadians), 0.0.switchColorAngle.toRadians)
        .splineToSplineHeading(Pose2d(40.0, 42.0.switchColor, 180.0.switchColorAngle.toRadians), 0.0.switchColorAngle.toRadians)
        .build()
    private val carouselToPark = drive.trajectoryBuilder((if (color == Color.BLUE) startToCarouselBlue else startToCarouselRed).end(), (if (color == Color.BLUE) startToCarouselBlue else startToCarouselRed).end().heading)
        .back(95.0)
        .build()


    fun createTrajectory(): ArrayList<Trajectory> {
        return hubPath()
    }

    fun hubPath(): ArrayList<Trajectory> {
        return arrayListOf(startToHub, hubToPark)
    }

    fun hubCarouselPath(): ArrayList<Trajectory> {
        return arrayListOf(if (color == Color.BLUE) startToCarouselBlue else startToCarouselRed, carouselToHub, hubToPark)
    }

    fun carouselPath(): ArrayList<Trajectory> {
        return arrayListOf(if (color == Color.BLUE) startToCarouselBlue else startToCarouselRed, carouselToPark)
    }

    fun parkFarPath(): ArrayList<Trajectory> {
        return arrayListOf(startToParkFar)
    }

    fun parkClosePath(): ArrayList<Trajectory> {
        return arrayListOf(startToParkClose)
    }

    fun drawOffbounds() {
        GraphicsUtil.fillRect(Vector2d(0.0, -63.0), 18.0, 18.0) // robot against the wall
    }
}

val Double.toRadians get() = (Math.toRadians(this))

val Double.switchColorAngle get () = (if (TrajectoryGen.color == TrajectoryGen.Color.BLUE) this else 360 - this)

val Double.flip get () = (if (TrajectoryGen.color == TrajectoryGen.Color.BLUE) this else {
    if(this - 180 < 0) this + 180
    this - 180
})

val Double.switchColor get () = (if (TrajectoryGen.color == TrajectoryGen.Color.BLUE) this else this * -1)