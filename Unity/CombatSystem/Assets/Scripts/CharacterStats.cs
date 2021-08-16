using UnityEngine;

public class CharacterStats : MonoBehaviour
{
    [SerializeField]
    public float health { get; private set; }
    public float maxHealth = 100;

    public int attackPower;
    public float moveSpeed;

    public float attackRadius;
    public float attackReload = 2;
    public float nextAttackTime;

    void Awake()
    {
        health = maxHealth;
    }

    public virtual void TakeDamage(int damage)
    {
        health -= damage;

        if(health <= 0)
        {
            Die();
        }
    }
    public virtual void Die() { }
}
