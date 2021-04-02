using System.Collections;
using System.Collections.Generic;
using UnityEngine;


public class CamScript : MonoBehaviour
{
    // The target GameObject the camera follows and rotates Around
    public GameObject target;

    // How far back the camera is from the offset
    [Range(.1f, 20.0f)]
    public float distance = 1.0f;

    // Offset of the camera position above the offset
    [Range(0.0f, 10.0f)]
    public float height = 1.0f;

    // The Speed of the camera rotation controlled by mouse movement
    [Range(10.0f, 100.0f)]
    public float rotationSpeed = 1.0f;

    // Indicates Weather to flip the rotation around the y-axis
    public bool invertMouse = false;

    // Offset from the target transforms position to the point the Camera looks at
    public Vector3 offset = Vector3.zero;

    public Vector2 scrollLimit = new Vector2(0.1f, 10.0f);
    [Range(0.0f, 100.0f)]
    public float scrollSpeed = 1.0f;
    
    // Indicates weather the camera should currently be rotated when mouse movement occurs
    private bool rotateCamera = false;


    void LateUpdate()
    {
        // Update the state of rotateCamera
        if (Input.GetMouseButtonDown(0))
        {
            rotateCamera = true;
        }
        else if (Input.GetMouseButtonUp(0))
        {
            rotateCamera = false;
        }

        // Initialize camera position
        transform.position = target.transform.position + offset;

        // Rotate
        if (rotateCamera)
        {
            transform.Rotate(target.transform.up, Time.deltaTime * rotationSpeed * Input.GetAxis("Mouse X") * (invertMouse ? 1 : -1));
        }
        // Extract xz component of the forward vector to go back in that direction by distance units
        Vector3 direction = transform.forward;
        direction.y = 0;
        direction.Normalize();

        // Update Distance
        distance = Mathf.Clamp(distance + (Input.mouseScrollDelta.y * scrollSpeed * Time.deltaTime), scrollLimit.x, scrollLimit.y);
        
        transform.position -= direction * distance;



        // Move up height units
        transform.position += Vector3.up * height;

        // Look at the target Position
        transform.LookAt(target.transform.position + offset);
    }
}