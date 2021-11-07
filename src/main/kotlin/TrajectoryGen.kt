import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.Trajectory

object TrajectoryGen {
    enum class Color {
        BLUE,
        RED
    }

    val color = Color.RED

    private val carouselStartPose = Pose2d(-36.0, 63.0.switchColor, if (color == Color.BLUE) 0.0.switchColorAngle.toRadians else 180.0.switchColorAngle.toRadians)
    private val farParkStartPose = Pose2d(-36.0, 63.0.switchColor, 180.0.switchColorAngle.toRadians)
    private val closeParkStartPose = Pose2d(6.0, 63.0.switchColor, 180.0.switchColorAngle.toRadians)
    private val hubFrontStartPose = Pose2d(-12.0, 63.0.switchColor, 90.0.switchColorAngle.toRadians)
    private val hubTopStartPose = Pose2d(6.0, 63.0.switchColor, 90.0.switchColorAngle.toRadians)

    private val drive = TrajectoryBuild(DriveConstantsComp)

    private val startToHubFront = drive.trajectoryBuilder(hubFrontStartPose, hubFrontStartPose.heading + 180.0.switchColorAngle.toRadians)
        .back(21.0)
        .build()
    private val startToHubTop = drive.trajectoryBuilder(hubTopStartPose, hubFrontStartPose.heading + 180.0.switchColorAngle.toRadians)
        .splineToSplineHeading(Pose2d(6.0, 24.0.switchColor, 0.0.switchColorAngle.toRadians), 270.0.switchColorAngle.toRadians)
        .build()

    private val startToCarouselBlue = drive.trajectoryBuilder(carouselStartPose, carouselStartPose.heading + 180.0.switchColorAngle.toRadians)
        .splineToConstantHeading(Vector2d(-53.5, 62.0.switchColor), 175.0.switchColorAngle.toRadians)
        .build()
    private val startToCarouselRed = drive.trajectoryBuilder(carouselStartPose, carouselStartPose.heading + 80.0.switchColorAngle.toRadians)
        .splineToSplineHeading(Pose2d(-56.5, 59.0.switchColor, 300.0.switchColorAngle.toRadians), 180.0.switchColorAngle.toRadians)
        .build()

    private val startToParkFar = drive.trajectoryBuilder(farParkStartPose, farParkStartPose.heading - 184.0.switchColorAngle.toRadians)
        .splineToConstantHeading(Vector2d(40.0, 61.0.switchColor), 0.0.switchColorAngle.toRadians)
        .build()
    private val startToParkClose = drive.trajectoryBuilder(closeParkStartPose, closeParkStartPose.heading - 190.0.switchColorAngle.toRadians)
        .splineToConstantHeading(Vector2d(40.0, 61.0.switchColor), 0.0.switchColorAngle.toRadians)
        .build()

    private val carouselToHubFront = drive.trajectoryBuilder((if (color == Color.BLUE) startToCarouselBlue else startToCarouselRed).end(), (if (color == Color.BLUE) startToCarouselBlue else startToCarouselRed).end().heading)
        .splineToSplineHeading(Pose2d(-12.0, 42.0.switchColor, 90.0.switchColorAngle.toRadians), 320.0.switchColorAngle.toRadians)
        .build()
    private val carouselToHubBottomBlue = drive.trajectoryBuilder(startToCarouselBlue.end(), startToCarouselBlue.end().heading - 90.0.toRadians)
        .splineToSplineHeading(Pose2d(-30.0, 24.0.switchColor, 180.0.switchColorAngle.toRadians), 320.0.switchColorAngle.toRadians)
        .build()
    private val carouselToHubBottomRed = drive.trajectoryBuilder(startToCarouselRed.end(), startToCarouselRed.end().heading + 0.0.toRadians)
        .splineToSplineHeading(Pose2d(-30.0, 24.0.switchColor, 180.0.switchColorAngle.toRadians), 320.0.switchColorAngle.toRadians)
        .build()

    private val hubFrontToPark = drive.trajectoryBuilder(carouselToHubFront.end(), carouselToHubFront.end().heading + 270.0.switchColorAngle.toRadians)
        .splineToSplineHeading(Pose2d(20.0, 42.0.switchColor, 0.0.switchColorAngle.toRadians), 0.0.switchColorAngle.toRadians)
        .splineToSplineHeading(Pose2d(40.0, 42.0.switchColor, 0.0.switchColorAngle.toRadians), 0.0.switchColorAngle.toRadians)
        .build()
    private val hubTopToParkIn = drive.trajectoryBuilder(startToHubTop.end(), startToHubTop.end().heading + 90.0.switchColorAngle.toRadians)
        .splineToSplineHeading(Pose2d(6.0, 32.0.switchColor, 0.0.switchColorAngle.toRadians), 90.0.switchColorAngle.toRadians)
        .splineToConstantHeading(Vector2d(40.0, 40.0.switchColor), 0.0.switchColorAngle.toRadians)
        .build()
    private val hubTopToParkOut = drive.trajectoryBuilder(startToHubTop.end(), startToHubTop.end().heading + 90.0.switchColorAngle.toRadians)
        .splineToSplineHeading(Pose2d(6.0, 53.0.switchColor, 0.0.switchColorAngle.toRadians), 90.0.switchColorAngle.toRadians)
        .splineToConstantHeading(Vector2d(40.0, 61.0.switchColor), 0.0.switchColorAngle.toRadians)
        .build()
    private val hubBottomToParkIn = drive.trajectoryBuilder((if (color == Color.BLUE) carouselToHubBottomBlue else carouselToHubBottomRed).end(),
        (if (color == Color.BLUE) carouselToHubBottomBlue else carouselToHubBottomRed).end().heading + 270.0.switchColorAngle.toRadians)
        .splineToSplineHeading(Pose2d(-30.0, 38.0.switchColor, 180.0.switchColorAngle.toRadians), 90.0.switchColorAngle.toRadians)
        .splineToConstantHeading(Vector2d(-12.0, 45.0.switchColor), 0.0.switchColorAngle.toRadians)
        .splineToConstantHeading(Vector2d(13.0, 40.0.switchColor), 0.0.switchColorAngle.toRadians)
        .splineToConstantHeading(Vector2d(40.0, 40.0.switchColor), 0.0.switchColorAngle.toRadians)
        .build()
    private val hubBottomToParkOut = drive.trajectoryBuilder((if (color == Color.BLUE) carouselToHubBottomBlue else carouselToHubBottomRed).end(),
        (if (color == Color.BLUE) carouselToHubBottomBlue else carouselToHubBottomRed).end().heading + 270.0.switchColorAngle.toRadians)
        .splineToSplineHeading(Pose2d(-30.0, 36.0.switchColor, 180.0.switchColorAngle.toRadians), 90.0.switchColorAngle.toRadians)
        .splineToConstantHeading(Vector2d(40.0, 61.0.switchColor), 0.0.switchColorAngle.toRadians)
        .build()

    private val carouselToPark = drive.trajectoryBuilder((if (color == Color.BLUE) startToCarouselBlue else startToCarouselRed).end(), (if (color == Color.BLUE)
        startToCarouselBlue.end().heading + 0.0.switchColorAngle.toRadians else startToCarouselRed.end().heading + 45.0.switchColorAngle.toRadians))
        .splineToSplineHeading(Pose2d(40.0, 61.0.switchColor, 0.0.switchColorAngle.toRadians), 0.0.switchColorAngle.toRadians)
        .build()


    fun createTrajectory(): ArrayList<Trajectory> {
        return carouselPath()
    }

    // ordered from simplest to most complicated

    fun parkClosePath(): ArrayList<Trajectory> {
        return arrayListOf(startToParkClose)
    }

    fun parkFarPath(): ArrayList<Trajectory> {
        return arrayListOf(startToParkFar)
    }

    fun carouselPath(): ArrayList<Trajectory> {
        return arrayListOf(if (color == Color.BLUE) startToCarouselBlue else startToCarouselRed, carouselToPark)
    }

    fun hubFrontPath(): ArrayList<Trajectory> {
        return arrayListOf(startToHubFront, hubFrontToPark)
    }

    fun hubTopParkInPath(): ArrayList<Trajectory> {
        return arrayListOf(startToHubTop, hubTopToParkIn)
    }

    fun hubTopParkOutPath(): ArrayList<Trajectory> {
        return arrayListOf(startToHubTop, hubTopToParkOut)
    }

    fun carouselHubPath(): ArrayList<Trajectory> {
        return arrayListOf(if (color == Color.BLUE) startToCarouselBlue else startToCarouselRed, carouselToHubFront, hubFrontToPark)
    }

    fun carouselHubBottomParkInPath(): ArrayList<Trajectory> {
        return arrayListOf(if (color == Color.BLUE) startToCarouselBlue else startToCarouselRed, if (color == Color.BLUE) carouselToHubBottomBlue else carouselToHubBottomRed, hubBottomToParkIn)
    }

    fun carouselHubBottomParkOutPath(): ArrayList<Trajectory> {
        return arrayListOf(if (color == Color.BLUE) startToCarouselBlue else startToCarouselRed, if (color == Color.BLUE) carouselToHubBottomBlue else carouselToHubBottomRed, hubBottomToParkOut)
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