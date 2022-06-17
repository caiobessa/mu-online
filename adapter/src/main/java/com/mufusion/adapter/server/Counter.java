package com.mufusion.adapter.server;

import lombok.Getter;

@Getter
public class Counter
{
    private  byte _maxCount;
    private  byte _minCount;
    private Object lockObject = new Object();
    private int _counter;

    /// <summary>
    /// Initializes a new instance of the <see cref="Counter"/> class.
    /// </summary>
    public Counter()
    {
        this._maxCount = (byte) 255;
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="Counter"/> class.
    /// </summary>
    /// <param name="min">The minimum counter value.</param>
    /// <param name="max">The maximum counter value.</param>
    public Counter(byte min, byte max)
    {
        this._minCount = min;
        this._maxCount = max;
    }

    /// <summary>
    /// Gets or sets the count.
    /// </summary>
    /// <value>
    /// The count.
    /// </value>
    public int getCounter(){
        return _counter;
    }

    /// <summary>
    /// Increases the counter value.
    /// When the maximum count is getting exceeded,
    /// the counter will jump back to the minimum counter value.
    /// </summary>
    public void Increase() {
            if (this._counter == this._maxCount) {
                this._counter = this._minCount;
            }
            else {
                this._counter++;
            }
    }

    /// <summary>
    /// Resets the count to the minimum value.
    /// </summary>
    public void Reset() {
        this._counter = this._minCount;
    }
}