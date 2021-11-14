import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint
import util.trajectories.ParallelTrajectoryBuilder

@Suppress("MayBeConstant", "unused")
object MecanumDrive {
    // inches
    private val TRACK_WIDTH = 18.0
    // inches per second
    private val MAX_VEL = 52.0
    private val MAX_ACCEL = 45.0
    // radians per second
    private val MAX_ANG_VEL = 3.1
    private val MAX_ANG_ACCEL = Math.toRadians(120.0)

    private val velConstraint: MinVelocityConstraint = MinVelocityConstraint(listOf(
        AngularVelocityConstraint(MAX_ANG_VEL),
        MecanumVelocityConstraint(MAX_VEL, TRACK_WIDTH)
    ))
    private val accelConstraint: ProfileAccelerationConstraint = ProfileAccelerationConstraint(MAX_ACCEL)


    fun trajectoryBuilder(startPose: Pose2d) =
        ParallelTrajectoryBuilder(TrajectoryBuilder(startPose, false, velConstraint, accelConstraint))

    fun trajectoryBuilder(startPose: Pose2d, reversed: Boolean) =
        ParallelTrajectoryBuilder(TrajectoryBuilder(startPose, reversed, velConstraint, accelConstraint))

    fun trajectoryBuilder(startPose: Pose2d, startHeading: Double) =
        ParallelTrajectoryBuilder(TrajectoryBuilder(startPose, startHeading, velConstraint, accelConstraint))

}