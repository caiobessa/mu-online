package com.mufusion.adapter.server;

public enum Variant {
    /// <summary>
    /// The newer variant, where the unencrypted block size is 8 bytes, and encrypted is 11 bytes.
    /// Uses a counter.
    /// </summary>
    New,

    /// <summary>
    /// The older variant, where the unencrypted block size is 32 bytes and encrypted is 38 bytes.
    /// Doesn't use a counter.
    /// </summary>
    Old,
}