using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Player : MonoBehaviour
{
    public static event System.Action OnReachEndOfLevel;

    public float moveSpeed = 7;
    public float smoothMoveTime = 0.1f;
    public float turnSpeed = 8;

    float angle;
    float smoothInputMagnitude;
    float smoothMoveVelocity;
    Vector3 velocity;

    Rigidbody rigid;
    bool disabled;

    void Start()
    {
        rigid = GetComponent<Rigidbody>();
        Guard.OnGuardSpottedPlayer += Disable;
    }

    void Update()
    {
        Vector3 inputDirection = Vector3.zero;

        if (!disabled)
        {
            inputDirection = new Vector3(Input.GetAxisRaw("Horizontal"), 0, Input.GetAxisRaw("Vertical"));
        }
        float inputMagnitude = inputDirection.magnitude;
        smoothInputMagnitude = Mathf.SmoothDamp(smoothInputMagnitude, inputMagnitude, ref smoothMoveVelocity, smoothMoveTime);

        float targetAngle = Mathf.Atan2(inputDirection.x, inputDirection.z) * Mathf.Rad2Deg;
        angle = Mathf.LerpAngle(angle, targetAngle, Time.deltaTime * turnSpeed * inputMagnitude);

        velocity = transform.forward * moveSpeed * smoothInputMagnitude;
    }

    void FixedUpdate()
    {
        rigid.MoveRotation(Quaternion.Euler(Vector3.up * angle));
        rigid.MovePosition(rigid.position + velocity * Time.deltaTime);
    }

    void OnTriggerEnter(Collider other)
    {
        if(other.tag == "Finish")
        {
            Disable();
            if(OnReachEndOfLevel != null)
            {
                OnReachEndOfLevel();
            }
        }    
    }

    void Disable()
    {
        disabled = true;
    }

    void OnDestroy()
    {
        Guard.OnGuardSpottedPlayer -= Disable;
    }
}
