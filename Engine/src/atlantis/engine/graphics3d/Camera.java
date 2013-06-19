package atlantis.engine.graphics3d;

import atlantis.framework.Matrix;
import atlantis.framework.Vector3;

/**
 * A camera is used to view a 3D scene.
 * @author Yannick
 *
 */
public class Camera {
	public Vector3 position;
	public Vector3 rotation;
	public Vector3 target;
	protected Vector3 reference;
	
	/**
	 * Create a camera with a position of (0, 0, 10).
	 */
	public Camera() {
		this.position = new Vector3(0.0f, 0.0f, 10.0f);
		this.rotation = new Vector3();
		this.target = new Vector3();
		this.reference = new Vector3(0.0f, 0.0f, 10.0f);
	}
	
	/**
	 * Translate the camera.
	 * @param x The X value.
	 * @param y The Y value.
	 * @param z The Z value.
	 */
	public void translate(float x, float y, float z) {
		Vector3 move = new Vector3(x, y, z);
		Matrix forwardMovement = Matrix.createRotationX(this.rotation.y);
		Vector3 v = Vector3.transform(move, forwardMovement);
		this.position.x += v.x;
		this.position.y += v.y;
		this.position.z += v.z;
	}
	
	/**
	 * Rotation the camera (radians)
	 * @param rx Angle on X axis.
	 * @param ry Angle on Y axis.
	 * @param rz Angle on Z axis.
	 */
	public void rotate(float rx, float ry, float rz) {
		this.rotation.x += rx;
		this.rotation.y += ry;
		this.rotation.z += rz;
	}
	
	/**
	 * Gets the view matrix for this camera.
	 * @return Return a view matrix.
	 */
	public Matrix getViewMatrix() {
		Matrix rotationMatrix = Matrix.multiply(Matrix.createRotationX(this.rotation.x), Matrix.createRotationY(this.rotation.y), Matrix.createRotationZ(this.rotation.z));
        Vector3 transformedRef = Vector3.transform(this.reference, rotationMatrix);
        this.target.x = this.position.x + transformedRef.x;
        this.target.y = this.position.y + transformedRef.y;
        this.target.z = this.position.z + transformedRef.z;
        return Matrix.createLookAt(this.position, this.target, Vector3.getUnitY());
	}
}