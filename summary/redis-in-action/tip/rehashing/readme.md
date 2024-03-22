
```c
int dictRehash(dict *d, int n) {
    int empty_visits = n*10; /* Max number of empty buckets to visit. */
    unsigned long s0 = DICTHT_SIZE(d->ht_size_exp[0]);
    unsigned long s1 = DICTHT_SIZE(d->ht_size_exp[1]);
    if (dict_can_resize == DICT_RESIZE_FORBID || !dictIsRehashing(d)) return 0;
    if (dict_can_resize == DICT_RESIZE_AVOID && 
        ((s1 > s0 && s1 / s0 < dict_force_resize_ratio) ||
         (s1 < s0 && s0 / s1 < dict_force_resize_ratio)))
    {
        return 0;
    }

    while(n-- && d->ht_used[0] != 0) {
        dictEntry *de, *nextde;

        /* Note that rehashidx can't overflow as we are sure there are more
         * elements because ht[0].used != 0 */
        assert(DICTHT_SIZE(d->ht_size_exp[0]) > (unsigned long)d->rehashidx);
        while(d->ht_table[0][d->rehashidx] == NULL) {
            d->rehashidx++;
            if (--empty_visits == 0) return 1;
        }
        de = d->ht_table[0][d->rehashidx];
        /* Move all the keys in this bucket from the old to the new hash HT */
        while(de) {
            uint64_t h;

            nextde = dictGetNext(de);
            void *key = dictGetKey(de);
            /* Get the index in the new hash table */
            if (d->ht_size_exp[1] > d->ht_size_exp[0]) {
                h = dictHashKey(d, key) & DICTHT_SIZE_MASK(d->ht_size_exp[1]);
            } else {
                /* We're shrinking the table. The tables sizes are powers of
                 * two, so we simply mask the bucket index in the larger table
                 * to get the bucket index in the smaller table. */
                h = d->rehashidx & DICTHT_SIZE_MASK(d->ht_size_exp[1]);
            }
            if (d->type->no_value) {
                if (d->type->keys_are_odd && !d->ht_table[1][h]) {
                    /* Destination bucket is empty and we can store the key
                     * directly without an allocated entry. Free the old entry
                     * if it's an allocated entry.
                     *
                     * TODO: Add a flag 'keys_are_even' and if set, we can use
                     * this optimization for these dicts too. We can set the LSB
                     * bit when stored as a dict entry and clear it again when
                     * we need the key back. */
                    assert(entryIsKey(key));
                    if (!entryIsKey(de)) zfree(decodeMaskedPtr(de));
                    de = key;
                } else if (entryIsKey(de)) {
                    /* We don't have an allocated entry but we need one. */
                    de = createEntryNoValue(key, d->ht_table[1][h]);
                } else {
                    /* Just move the existing entry to the destination table and
                     * update the 'next' field. */
                    assert(entryIsNoValue(de));
                    dictSetNext(de, d->ht_table[1][h]);
                }
            } else {
                dictSetNext(de, d->ht_table[1][h]);
            }
            d->ht_table[1][h] = de;
            d->ht_used[0]--;
            d->ht_used[1]++;
            de = nextde;
        }
        d->ht_table[0][d->rehashidx] = NULL;
        d->rehashidx++;
    }

    /* Check if we already rehashed the whole table... */
    if (d->ht_used[0] == 0) {
        if (d->type->rehashingCompleted) d->type->rehashingCompleted(d);
        zfree(d->ht_table[0]);
        /* Copy the new ht onto the old one */
        d->ht_table[0] = d->ht_table[1];
        d->ht_used[0] = d->ht_used[1];
        d->ht_size_exp[0] = d->ht_size_exp[1];
        _dictReset(d, 1);
        d->rehashidx = -1;
        return 0;
    }

    /* More to rehash... */
    return 1;
}
```
