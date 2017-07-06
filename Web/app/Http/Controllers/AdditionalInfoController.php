<?php

namespace App\Http\Controllers;

use App\AdditionalInfo;
use Illuminate\Http\Request;

class AdditionalInfoController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return AdditionalInfo::all();
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $data = $request->all();
        
        $additionalInfo = AdditionalInfo::create($data);

        return response()->json($additionalInfo, 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\AdditionalInfo  $additionalInfo
     * @return \Illuminate\Http\Response
     */
    public function show(AdditionalInfo $additionalInfo)
    {
        return $additionalInfo;
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\AdditionalInfo  $additionalInfo
     * @return \Illuminate\Http\Response
     */
    public function edit(AdditionalInfo $additionalInfo)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\AdditionalInfo  $additionalInfo
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, AdditionalInfo $additionalInfo)
    {
        $data = $request->all();

        if (array_key_exists('info', $data)) {
            $additionalInfo->info = $data['info'];
        }

        $additionalInfo->save();

        return response()->json($additionalInfo, 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\AdditionalInfo  $additionalInfo
     * @return \Illuminate\Http\Response
     */
    public function destroy(AdditionalInfo $additionalInfo)
    {
        $additionalInfo->delete();

        return response()->json('Deleted', 200);
    }

    /**
     * Search the specified resource from storage by parameter.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\AdditionalInfo  $additionalInfo
     * @param  Parameter  $param
     * @param  Text  $text
     * @return \Illuminate\Http\Response
     */
    public function searchByParam(Request $request, AdditionalInfo $additionalInfo, $param = 'info', $text)
    {
        return $additionalInfo
            ->where($param,
                Operator::LIKE,
                '%'.$text.'%')
            ->get();
    }
}