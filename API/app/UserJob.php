<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class UserJob extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'userId',
        'jobId',
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [ ];

    /**
     * Get the user record associated with the userJob.
     */
    public function user()
    {
        return $this->belongsTo('App\User', 'userId');
    }

    /**
     * Get the job record associated with the userJob.
     */
    public function job()
    {
        return $this->belongsTo('App\Job', 'jobId');
    }
}
