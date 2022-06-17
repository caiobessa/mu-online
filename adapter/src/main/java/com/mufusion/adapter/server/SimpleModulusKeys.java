package com.mufusion.adapter.server;// <copyright file="SimpleModulusKeys.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>


import lombok.Getter;

/// <summary>
/// Class to hold encryption keys for the simple modulus algorithm.
/// </summary>
@Getter
public class SimpleModulusKeys {
    /// <summary>
    /// Initializes a new instance of the <see cref="SimpleModulusKeys"/> class.
    /// </summary>
    /// <param name="modulusKey">The modulus key.</param>
    /// <param name="xorKey">The xor key.</param>
    /// <param name="encryptKey">The encrypt key.</param>
    /// <param name="decryptKey">The decrypt key.</param>
    long[] modulusKey = {0, 0, 0, 0};
    long[] xorKey = {0, 0, 0, 0};
    long[] encryptKey = {0, 0, 0, 0};
    long[] decryptKey = {0, 0, 0, 0};


    public SimpleModulusKeys(long[] modulusKey, long[] xorKey, long[] encryptKey, long[] decryptKey) {
        this.modulusKey = modulusKey;
        this.xorKey = xorKey;
        this.encryptKey = encryptKey;
        this.decryptKey = decryptKey;
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="SimpleModulusKeys"/> class.
    /// </summary>
    public SimpleModulusKeys() {
    }


    ;

    /// <summary>
    /// Creates an instance of <see cref="SimpleModulusKeys"/> with the crypt key as <see cref="DecryptKey"/>.
    /// </summary>
    /// <param name="decryptionKey">The decryption key with 12 integers.</param>
    /// <returns>An instance of <see cref="SimpleModulusKeys"/> with the crypt key as <see cref="DecryptKey"/>.</returns>
    public static SimpleModulusKeys CreateDecryptionKeys(long[] decryptionKey) {
        var keys = new SimpleModulusKeys();
        keys.modulusKey[0] = decryptionKey[0];
        keys.modulusKey[1] = decryptionKey[1];
        keys.modulusKey[2] = decryptionKey[2];
        keys.modulusKey[3] = decryptionKey[3];
        keys.decryptKey[0] = decryptionKey[4];
        keys.decryptKey[1] = decryptionKey[5];
        keys.decryptKey[2] = decryptionKey[6];
        keys.decryptKey[3] = decryptionKey[7];
        keys.xorKey[0] = decryptionKey[8];
        keys.xorKey[1] = decryptionKey[9];
        keys.xorKey[2] = decryptionKey[10];
        keys.xorKey[3] = decryptionKey[11];
        return keys;
    }

    /// <summary>
    /// Creates an instance of <see cref="SimpleModulusKeys"/> with the crypt key as <see cref="EncryptKey"/>.
    /// </summary>
    /// <param name="encryptionKey">The decryption key with 12 integers.</param>
    /// <returns>An instance of <see cref="SimpleModulusKeys"/> with the crypt key as <see cref="EncryptKey"/>.</returns>
    public static SimpleModulusKeys CreateEncryptionKeys(long[] encryptionKey) {
        var keys = new SimpleModulusKeys();
        keys.modulusKey[0] = encryptionKey[0];
        keys.modulusKey[1] = encryptionKey[1];
        keys.modulusKey[2] = encryptionKey[2];
        keys.modulusKey[3] = encryptionKey[3];
        keys.encryptKey[0] = encryptionKey[4];
        keys.encryptKey[1] = encryptionKey[5];
        keys.encryptKey[2] = encryptionKey[6];
        keys.encryptKey[3] = encryptionKey[7];
        keys.xorKey[0] = encryptionKey[8];
        keys.xorKey[1] = encryptionKey[9];
        keys.xorKey[2] = encryptionKey[10];
        keys.xorKey[3] = encryptionKey[11];
        return keys;
    }



    /// <summary>
    /// Gets the encryption key values for the <see cref="PipelinedSimpleModulusEncryptor"/>.
    /// </summary>
    /// <returns>The encryption key values for the <see cref="PipelinedSimpleModulusEncryptor"/>.</returns>
    public long[] getEncryptionKeys() {
        //return this.modulusKey.Concat(this.EncryptKey).Concat(this.XorKey).ToArray();
        return null;
    }

    /// <summary>
    /// Gets the decryption key values for the <see cref="PipelinedSimpleModulusDecryptor"/>.
    /// </summary>
    /// <returns>The decryption key values for the <see cref="PipelinedSimpleModulusDecryptor"/>.</returns>
    public long[] getDecryptionKeys() {
        // return this.modulusKey.Concat(this.DecryptKey).Concat(this.XorKey).ToArray();
        return null;
    }
}